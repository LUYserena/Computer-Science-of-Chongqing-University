`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/24 14:56:08
// Design Name: 
// Module Name: Moore_1101
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module Moore_1101(clk,set,rst_n,data,light);
   input clk;
    input set;
    input rst_n;
    input [7:0] data;
    output reg light;
    
    wire z;
    parser tryone(.data(data),.clk(clk),.set(set),.out(z));
    
    parameter s0 = 5'b00001;
    parameter s1 = 5'b00010;
    parameter s2 = 5'b00100;
    parameter s3 = 5'b01000;
    parameter s4 = 5'b10000;
    
    reg [4:0] currState;
    reg [4:0] nextState;
    
    always@(posedge clk or negedge rst_n)
    begin
        if(!rst_n)
            currState <= s0;
        else
            currState <= nextState;
      end
      
      always@(*)
        begin
            if(!rst_n)
                nextState <= s0;
             else
                begin
                    case(currState)
                    s0: nextState<=(z==1)?s1:s0;
                    s1: nextState<=(z==1)?s2:s0;
                    s2: nextState<=(z==1)?s2:s3;
                    s3: nextState<=(z==1)?s4:s0;
                    s4: nextState<=(z==1)?s2:s0;
                    default:nextState <= s0;
                    endcase
                 end
             end
           
       always@(*)
            begin
                if(!rst_n)
                    light <= 0;
                else if(currState==s4)
                    light <= 1;
               end  
endmodule
